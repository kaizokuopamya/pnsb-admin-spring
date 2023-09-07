package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.MainMenu;

public interface MainMenuRepository extends JpaRepository<MainMenu, Integer> {

	MainMenu findByid(int menuId);

	List<MainMenu> findAllByOrderByMenuname();
	
	List<MainMenu> findAllByOrderByIdDesc();

	//List<MainMenu> findByStatus(int i);

}
