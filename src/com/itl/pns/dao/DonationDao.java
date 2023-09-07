package com.itl.pns.dao;
import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.BeneficaryMasterEntity;
import com.itl.pns.entity.Donation;

public interface DonationDao {

	public List<Donation> getDonations();

	public List<Donation> getDonationById(int id);
	
	public ResponseMessageBean checkIsAccNoExistForSameTrustName(Donation donation);
	
	public ResponseMessageBean checIsAccNoExistForSameTrustNameWhileUpdate(Donation donation);
	
	
	public List<BeneficaryMasterEntity> getBeneficaryList();

	public List<BeneficaryMasterEntity> getBeneficaryById(int id);

	

}
