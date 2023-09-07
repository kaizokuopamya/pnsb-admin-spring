package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.entity.Donation;

public interface CorpDonationService {

	public List<Donation> getCorpDonations();

	public List<Donation> getCorpDonationById(int id);

	public boolean saveCorpDonationDetails(Donation donation);

	public Boolean updateCorpDonationDetails(Donation donation);
	
	public boolean saveCorpDonationToAdminWorkFlow(Donation donation,int userStatus);
}
