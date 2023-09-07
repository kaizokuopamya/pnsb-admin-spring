package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.CalculatorDao;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.CalculatorMasterEntity;
import com.itl.pns.service.CalculatorService;

@Service
public class CalculatorServiceImpl implements CalculatorService{

	@Autowired
	CalculatorDao calculatorDao;
	
	@Override
	public List<CalculatorMasterEntity> getCalculatorMasterById(int id) {
		return calculatorDao.getCalculatorMasterById(id);

	}

	@Override
	public List<CalculatorMasterEntity> getCalculatorMasterDetails() {

		return calculatorDao.getCalculatorMasterDetails();
	}

	@Override
	public boolean addCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster) {
		return calculatorDao.addCalculatorMasterDetails(calculatorMaster);
	}

	@Override
	public boolean updateCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster) {
		return calculatorDao.updateCalculatorMasterDetails(calculatorMaster);

	}

	@Override
	public List<CalculatorFormulaEntity> getCalculatorFormulaById(int id) {
		return calculatorDao.getCalculatorFormulaById(id);
	}

	@Override
	public List<CalculatorFormulaEntity> getCalculatorFormulaDetails() {
		return calculatorDao.getCalculatorFormulaDetails();
	}

	@Override
	public boolean addCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData) {
		return calculatorDao.addCalculatorFormulaDetails(calculatorFormulaData);
	}

	@Override
	public boolean updateCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData) {
		return calculatorDao.updateCalculatorFormulaDetails(calculatorFormulaData);
	}

	@Override
	public ResponseMessageBean isCalculatorExit(CalculatorMasterEntity calculatorDetails) {
		return calculatorDao.isCalculatorExit(calculatorDetails);
	}

	@Override
	public ResponseMessageBean updateCalculatorExit(CalculatorMasterEntity calculatorDetails) {
		return calculatorDao.updateCalculatorExit(calculatorDetails);
	}

}
