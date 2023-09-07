package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.entity.Donation;

public interface CorpDonationDao {

	public List<Donation> getCorpDonations();

	public List<Donation> getCorpDonationById(int id);

}
