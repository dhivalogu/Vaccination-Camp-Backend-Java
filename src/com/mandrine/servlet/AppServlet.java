package com.mandrine.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mandrine.cache.CacheDB;
import com.mandrine.exception.ExistingDataException;
import com.mandrine.exception.InvalidRequestException;
import com.mandrine.exception.SlotOverflowException;
import com.mandrine.model.Account;
import com.mandrine.model.Booking;
import com.mandrine.model.Camp;
import com.mandrine.model.City;
import com.mandrine.model.People;
import com.mandrine.model.Slot;
import com.mandrine.service.AccountService;
import com.mandrine.service.AdminService;
import com.mandrine.service.BookingService;
import com.mandrine.util.CommonUtil;
import com.mandrine.util.JSONUtil;

public class AppServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Queue<String> resourceQueue = CommonUtil.parseRequest(request);
			String responseJSON = null;
			String currentResource = null;
			List responseList = null;
			switch (resourceQueue.poll()) {
			case "cities":
				int cityID;
				currentResource = resourceQueue.poll();
				if (currentResource == null) {

					List<City> cityData = new ArrayList<City>(BookingService.getCityData().values());
					if (request.getParameter("sortBy") != null) {
						if (request.getParameter("sortBy").equals("vaccinatedCount")) {
							cityData.sort((left, right) -> right.getVaccinatedCount() - left.getVaccinatedCount());
						} else if (request.getParameter("sortBy").equals("stock")) {
							cityData.sort((left, right) -> right.getStock() - left.getStock());
						} else
							throw new InvalidRequestException("Wrong Sort By Value");
					}
					responseList = cityData;
					responseJSON = new Gson().toJson(cityData);
					break;
				}

				else if (CommonUtil.isNumber(currentResource)) {
					cityID = Integer.parseInt(currentResource);
					currentResource = resourceQueue.poll();
					City cityData = BookingService.getCityById(cityID);
					if (currentResource == null) {

						responseJSON = new Gson().toJson(cityData);
						break;
					} else if (!currentResource.equals("camps"))
						throw new InvalidRequestException();
					else {
						currentResource = resourceQueue.poll();
						Camp campData = cityData.getCamp();
						if (currentResource == null) {
							responseJSON = new Gson().toJson(campData);
							break;
						} else if (!currentResource.equals("slots"))
							throw new InvalidRequestException();
						else {
							List<Slot> slotList = campData.getSlotList();
							currentResource = resourceQueue.poll();
							if (currentResource == null) {
								responseJSON = new Gson().toJson(slotList);
							} else if (!CommonUtil.isNumber(currentResource))
								throw new InvalidRequestException();
							else {
								int slotID = Integer.parseInt(currentResource);
								Slot slotData = null;
								for (Slot slot : slotList) {
									if (slot.getSlotID() == slotID) {
										slotData = slot;

									}
								}
								currentResource = resourceQueue.poll();

								if (currentResource == null) {
									responseJSON = new Gson().toJson(slotData);
									break;
								} else if (currentResource.equals("bookings")) {

									List<Booking> bookingList = slotData.getBookingList();
									responseJSON = new Gson().toJson(bookingList);
									break;

								} else
									throw new InvalidRequestException();
							}

						}

					}
				} else
					throw new InvalidRequestException();
				break;
			case "people":
				currentResource = resourceQueue.poll();
				if (currentResource == null) {
					List<People> people = new ArrayList<People>(AccountService.getPeopleData());
					if (request.getParameter("filter") != null) {
						if (request.getParameter("filter").equals("vaccinated")) {
							people.removeIf(person -> person.getDosageCount() < 2);
						} else
							throw new InvalidRequestException("Invalid Filter Value");
					}

					responseJSON = new Gson().toJson(people);
				} else if (CommonUtil.isValidAadhar(currentResource)) {
					People person = AccountService.getPeopleByID(currentResource);
					currentResource = resourceQueue.poll();
					if (currentResource == null) {
						responseJSON = new Gson().toJson(person);
						break;
					} else if (currentResource.matches("bookings")) {
						Collection<Booking> bookingList = person.getBookingList();
						responseJSON = new Gson().toJson(bookingList);
					} else
						throw new InvalidRequestException();

				} else
					throw new InvalidRequestException();
				break;

			}

			response.getWriter().print(responseJSON);

		} catch (NullPointerException e) {
			JSONObject responseJSON = new JSONObject();
			responseJSON.put("Error", "No data found for given values");
			response.getWriter().print(responseJSON);
			e.printStackTrace();
		} catch (Exception e) {
			JSONObject responseJSON = new JSONObject();
			responseJSON.put("Error", e);
			response.getWriter().print(responseJSON);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJSON = new JSONObject();

		try {
			JSONObject requestJSON = JSONUtil.toJSON(request);
			Queue<String> resourceQueue = CommonUtil.parseRequest(request);
			String currentResource = resourceQueue.poll();
			switch (currentResource) {
			case "accounts":
				currentResource = resourceQueue.poll();

				if (currentResource.equals("authentication")) {

					Account account = new Account();
					account.setUsername(requestJSON.getString("username"));
					account.setPassword(requestJSON.getString("password"));
					if (AccountService.userAuthentication(account)) {

						responseJSON.put("username", account.getUsername());
						responseJSON.put("accessLevel", account.getAccessLevel());
						responseJSON.put("status-code", 200);
					} else {
						responseJSON.put("status-code", 401);
					}
					response.getWriter().print(responseJSON);
					break;
				} else
					throw new InvalidRequestException();
			case "cities":
				currentResource = resourceQueue.poll();
				if (currentResource == null) {
					City city = new City();
					city.setName(requestJSON.getString("cityName"));
					city.setStock(requestJSON.getInt("stock"));
					AdminService.addCity(city);
					response.getWriter().print(new Gson().toJson(city));
					response.setStatus(201);
					break;

				} else if (currentResource.equals("camps")) {
					currentResource = resourceQueue.poll();
					if (currentResource == null) {
						Camp camp = new Camp();
						camp.setCityID(requestJSON.getInt("cityID"));
						camp.setAddress(requestJSON.getString("address"));
						camp.setBeginDate(requestJSON.getString("beginDate"));
						camp.setEndDate(requestJSON.getString("endDate"));
						camp.calcAvailDate();
						AdminService.addCamp(camp);
						responseJSON.put("message", "Camp Added Successfully");
						responseJSON.put("status-code", 201);
						response.getWriter().print(responseJSON);
					} else if (currentResource.equals("slots")) {
						currentResource = resourceQueue.poll();
						if (currentResource.equals("bookings")) {
							String responseJSON1 = null;
							System.out.println("At Booking");
							Booking bookingData = new Booking();
							bookingData.setAADHAR(requestJSON.getString("aadharID"));
							bookingData.setSlotID(requestJSON.getInt("slotID"));
							bookingData.setCampID(requestJSON.getInt("campID"));
							bookingData.setCityID(requestJSON.getInt("cityID"));
							responseJSON1 = new Gson().toJson(BookingService.addBooking(bookingData));
							response.setStatus(201);
							response.getWriter().print(responseJSON1);
						} else
							throw new InvalidRequestException();

					} else
						throw new InvalidRequestException();
				} else
					throw new InvalidRequestException();
				break;

			case "people":
				People person = new People();
				person.setID(requestJSON.getString("AADHAR"));
				person.setFirstName(requestJSON.getString("firstName"));
				person.setLastName(requestJSON.getString("lastName"));
				person.setMobile(requestJSON.getString("mobile"));
				person.setDOB(requestJSON.getString("DOB"));
				person.setDosageCount(-1);
				AccountService.addPerson(person);
				responseJSON.put("ID", person.getID());
				responseJSON.put("status-code", 201);
				responseJSON.put("status", "Account Created Successfully");
				response.getWriter().print(responseJSON);
				break;
			}

		} catch (SlotOverflowException e) {

			responseJSON.put("status-code", 403);
			responseJSON.put("Error:", e);
			response.getWriter().print(responseJSON);
		} catch (JSONException e) {
			responseJSON.put("Error", "Proper Request Body Required!");
			e.printStackTrace();

		} catch (ExistingDataException e) {
			responseJSON.put("status-code", "409");
			responseJSON.put("Error", e);
		} catch (Exception e) {
			responseJSON.put("Error", e);
			e.printStackTrace();
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJSON = new JSONObject();
		try {
			JSONObject requestJSON = JSONUtil.toJSON(request);
			Queue<String> resourceQueue = CommonUtil.parseRequest(request);
			String currentResource = resourceQueue.poll();
			if (currentResource.equals("cities")) {
				currentResource = resourceQueue.poll();
				if (CommonUtil.isNumber(currentResource)) {
					int stock = requestJSON.getInt("stock");
					int cityID = Integer.parseInt(currentResource);
					AdminService.updateStock(cityID, stock);
					responseJSON.put("message", "Stock of given city updated successfully");
				} else if (currentResource.equals("camps")) {
					if (resourceQueue.poll().equals("slots") && resourceQueue.poll().equals("bookings")) {
						Booking bookingDetails = CacheDB.getBookingCache().get(requestJSON.getInt("bookingID"));
						AdminService.vaccinated(bookingDetails);
						responseJSON.put("message", "" + bookingDetails.getAADHAR() + " got vaccinated");
					} else
						throw new InvalidRequestException();
				} else
					throw new InvalidRequestException();
			} else
				throw new InvalidRequestException();
		} catch (Exception e) {
			response.getWriter().print(e);
			response.setStatus(404);
			e.printStackTrace();
		} finally {
			response.getWriter().print(responseJSON);
		}

	}
}
