package com.mandrine.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import com.mandrine.DAO.AccountDAO;
import com.mandrine.DAO.PeopleDAO;
import com.mandrine.cache.CacheDB;
import com.mandrine.exception.ExistingDataException;
import com.mandrine.model.Account;
import com.mandrine.model.People;

public class AccountService {

	public static Boolean userAuthentication(Account account) throws JSONException, SQLException {
		ResultSet rs=AccountDAO.fetch(account);
		if (rs.next()) {
			int accessLevel = Integer.parseInt(rs.getString("ACCESS_LEVEL"));
			account.setAccessLevel(accessLevel);
			return true;
		}
		return false;
	}

	public static JSONObject addPerson(People person) throws PSQLException, JSONException, SQLException, ParseException, ExistingDataException {

		JSONObject responseJSON = new JSONObject();

		if (!AccountService.isPersonExists(person)) {
			PeopleDAO.create(person);
			CacheDB.getPeopleCache().put(person.getID(), person);
			Account account = new Account();
			account.setUsername(person.getID());
			account.setPassword(person.getID().substring(8) + person.getMobile().substring(6));
			account.setAccessLevel(1);
			AccountDAO.create(account);

		} else {
			throw new ExistingDataException("Account Already Exists");
		}
		return responseJSON;
	}

	public static boolean isPersonExists(People person) throws SQLException {
		return CacheDB.getPeopleCache().containsKey(person.getID());
	}

	public static People getPeopleByID(String id) throws SQLException {
		return CacheDB.getPeopleCache().get(id);
	}

	public static Collection<People> getPeopleData() throws SQLException {
		return CacheDB.getPeopleCache().values();
	}

}
