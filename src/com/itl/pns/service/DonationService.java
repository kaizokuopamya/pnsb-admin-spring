package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.BeneficaryMasterEntity;
import com.itl.pns.entity.Donation;

public interface DonationService {

	public List<Donation> getDonations();

	public List<Donation> getDonationById(int id);

	public boolean saveDonationDetails(Donation donation);

	public Boolean updateDonationDetails(Donation donation);
	
	public boolean saveDonationToAdminWorkFlow(Donation donation,int userStatus);
	
	
	
	
	public List<BeneficaryMasterEntity> getBeneficaryList();

	public List<BeneficaryMasterEntity> getBeneficaryById(int id);

	public boolean saveBeneficaryList(BeneficaryMasterEntity donation);

	public Boolean updateBeneficaryList(BeneficaryMasterEntity donation);
	
	public boolean saveBeneficaryToAdminWorkFlow(BeneficaryMasterEntity donation,int userStatus);
	
	
	
	
	
	
	

}
