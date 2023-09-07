package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.CalculatorMasterEntity;



public interface CalculatorService {

	public List<CalculatorMasterEntity> getCalculatorMasterById(int id);

	public List<CalculatorMasterEntity> getCalculatorMasterDetails();

	public boolean addCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster);

	public boolean updateCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster);

	public List<CalculatorFormulaEntity> getCalculatorFormulaById(int id);

	public List<CalculatorFormulaEntity> getCalculatorFormulaDetails();

	public boolean addCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData);

	public boolean updateCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData);
	
	public ResponseMessageBean isCalculatorExit(CalculatorMasterEntity calculatorDetails);
	
	public ResponseMessageBean updateCalculatorExit(CalculatorMasterEntity calculatorDetails);
}
