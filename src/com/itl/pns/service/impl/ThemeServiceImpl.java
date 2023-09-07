package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.ThemesBean;
import com.itl.pns.dao.ThemeDao;
import com.itl.pns.entity.ThemeMenuOptionEntity;
import com.itl.pns.entity.ThemeNameEntity;
import com.itl.pns.entity.ThemeSideBarBackgroundEntity;
import com.itl.pns.entity.ThemeSideBarColorEntity;
import com.itl.pns.entity.ThemesEntity;
import com.itl.pns.entity.ThemesSubEntity;
import com.itl.pns.repository.ThemesRepository;
import com.itl.pns.repository.ThemesSubRepository;
import com.itl.pns.service.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {
	static Logger LOGGER = Logger.getLogger(ThemeServiceImpl.class);
	
    @Autowired
	ThemeDao themeDao;
    
    @Autowired
    ThemesRepository themesRepository;
    
    @Autowired
    ThemesSubRepository themesSubRepository;
    
	@Override
	public List<ThemesBean> getThemeNamesById(int id) {
		return themeDao.getThemeNamesById(id);
	}

	@Override
	public List<ThemesBean> getAllThemeNames() {
		return themeDao.getAllThemeNames();
	}

	@Override
	public List<ThemesBean> getThemesSideBarColorById(int id) {
		return themeDao.getThemesSideBarColorById(id);
	}

	@Override
	public List<ThemesBean> getAllThemesSideBarColor() {
		return themeDao.getAllThemesSideBarColor();
	}

	@Override
	public List<ThemesBean> getThemesBackgroundColorById(int id) {
		return themeDao.getThemesBackgroundColorById(id);
	}

	@Override
	public List<ThemesBean> getAllThemesBackgroundColor() {
		return themeDao.getAllThemesBackgroundColor();
	}

	@Override
	public List<ThemesBean> getThemeMenuOptionById(int id) {
		return themeDao.getThemeMenuOptionById(id);
	}

	@Override
	public List<ThemesBean> getAllThemeMenuOption() {
		return themeDao.getAllThemeMenuOption();
	}

	@Override
	public boolean addThemeNames(ThemeNameEntity themeData) {
		return themeDao.addThemeNames(themeData);
	}

	@Override
	public boolean addThemeSideBarColor(ThemeSideBarColorEntity themeData) {
		return themeDao.addThemeSideBarColor(themeData);
	}

	@Override
	public boolean addThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData) {
		return themeDao.addThemesBackgroundColor(themeData);
	}

	@Override
	public boolean addAllThemeMenuOption(ThemeMenuOptionEntity themeData) {
		return themeDao.addAllThemeMenuOption(themeData);
	}

	@Override
	public boolean saveTheme(ThemesEntity themeData) {
		return themeDao.saveTheme(themeData);
	}

	@Override
	public boolean updateThemes(ThemesEntity themesData) {
		return themeDao.updateThemes(themesData);
	}

	@Override
	public List<ThemesEntity> getAllThemes() {
		return themeDao.getAllThemes();
	}

	@Override
	public List<ThemesEntity> getThemesById(int id) {
		return themeDao.getThemesById(id);
	}

	@Override
	public boolean updateThemeNames(ThemeNameEntity themeData) {
		return themeDao.updateThemeNames(themeData);
	}

	@Override
	public boolean updateThemeSideBarColor(ThemeSideBarColorEntity themeData) {
		return themeDao.updateThemeSideBarColor(themeData);
	}

	@Override
	public boolean updateThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData) {
		return themeDao.updateThemesBackgroundColor(themeData);	}

	@Override
	public boolean updateThemeMenuOption(ThemeMenuOptionEntity themeData) {
		
		return themeDao.updateThemeMenuOption(themeData);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<ThemesEntity> getThemes(String id) {
		List<ThemesEntity> template = themesRepository.getThemes(new BigDecimal (id));
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<ThemesSubEntity> getSubDetails(BigDecimal id) {
		List<ThemesSubEntity> template = themesSubRepository.getThemesthemesSub(id);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean addThemes(ThemesEntity themesEntity) {try {
		LOGGER.info("ThemeServiceImpl->addThemes----------Start");
		ThemesEntity themesE = null;
		List<ThemesSubEntity> themesSubE = new ArrayList<ThemesSubEntity>();
		themesSubE.addAll(themesEntity.getThemesSubEntity());
		
		themesEntity.setCreatedon(new Date());
		themesE = themesRepository.save(themesEntity);
		
		for(ThemesSubEntity ts : themesSubE ) {
			ts.setThemes_id(themesE.getId());
			ts.setThemename(themesEntity.getThemeName());
			ts.setCreatedon(new Date());
			ts.setUpdatedon(new Date());
			ts.setStatusid(themesEntity.getStatusid());
			ts.setCreatedby(themesEntity.getCreatedby());
			ts.setUpdatedby(themesEntity.getUpdatedby());			
			themesSubRepository.save(ts);
		}
	} catch (Exception e) {
		LOGGER.info("Exception:", e);
		return false;
	}
	LOGGER.info("ThemeServiceImpl->addThemes----------End");
	return true;
 }

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean editThemesDetails(ThemesEntity themesEntity) {
		try {
		LOGGER.info("ThemeServiceImpl->editThemesDetails----------Start");
		ThemesEntity themesE = null;
		List<ThemesSubEntity> themesSubE = new ArrayList<ThemesSubEntity>();
		themesRepository.updateThemes(themesEntity.getId(),themesEntity.getFromDate(),themesEntity.getToDate(),themesEntity.getUpdatedby(),new Date(),themesEntity.getThemeMenuOptions(),themesEntity.getStatusid());
		themesSubE.addAll(themesEntity.getThemesSubEntity());
		
		for(ThemesSubEntity ts : themesSubE ) {
			themesSubRepository.updateThemesSub(ts.getId(),ts.getTextcolor(),ts.getBarbackgroundcolor(),themesEntity.getUpdatedby(),new Date(),themesEntity.getStatusid());
		}
	} catch (Exception e) {
		LOGGER.info("Exception:", e);
		return false;
	}
	LOGGER.info("ThemeServiceImpl->editThemesDetails----------End");
	return true;
 }

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public ResponseMessageBean checkThemes(String id1) {
		LOGGER.info("ThemeServiceImpl->checkThemes----------Start");
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlThemesExist = themesRepository.checkThemes(id1);


			if (!(sqlThemesExist.toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Themes Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Themes not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		LOGGER.info("ThemeServiceImpl->checkThemes----------End");
		return rmb;
	}


}
