package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.ThemesBean;
import com.itl.pns.entity.ThemeMenuOptionEntity;
import com.itl.pns.entity.ThemeNameEntity;
import com.itl.pns.entity.ThemeSideBarBackgroundEntity;
import com.itl.pns.entity.ThemeSideBarColorEntity;
import com.itl.pns.entity.ThemesEntity;

public interface ThemeDao {

	public List<ThemesBean> getThemeNamesById(int id);

	public List<ThemesBean> getAllThemeNames();
	
	

	public List<ThemesBean> getThemesSideBarColorById(int id);

	public List<ThemesBean> getAllThemesSideBarColor();
	
	

	public List<ThemesBean> getThemesBackgroundColorById(int id);

	public List<ThemesBean> getAllThemesBackgroundColor();
	
	

	public List<ThemesBean> getThemeMenuOptionById(int id);

	public List<ThemesBean> getAllThemeMenuOption();
	
	
	public boolean addThemeNames(ThemeNameEntity themeData);

	public boolean addThemeSideBarColor(ThemeSideBarColorEntity themeData);

	public boolean addThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData);

	public boolean addAllThemeMenuOption(ThemeMenuOptionEntity themeData);
	
	public boolean updateThemeNames(ThemeNameEntity themeData);

	public boolean updateThemeSideBarColor(ThemeSideBarColorEntity themeData);

	public boolean updateThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData);
	
	public boolean updateThemeMenuOption(ThemeMenuOptionEntity themeData);
	
	public boolean saveTheme(ThemesEntity themeData);
	
	
	public boolean updateThemes(ThemesEntity themesData);

	public List<ThemesEntity> getAllThemes();

	public List<ThemesEntity> getThemesById(int id);

	public ResponseMessageBean checkUpdateIsThemeExistForSameDate(ThemesEntity themeData);
	
	public ResponseMessageBean checkIsThemeExistForSameDate(ThemesEntity themeData);
}
