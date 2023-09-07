package com.itl.pns.bean;

import java.math.BigInteger;
import java.util.Date;

public class ImpsStationBean {

	BigInteger id;
	String name;
	String details;
	String station_type;
	String zmk;
	String zpk;
	String new_zpk;
	char signed_on;
	char connected;
	Date last_connect;
	Date last_disconnect;
	Date last_echo;
	Date last_zpk_change;
	Date last_signon;
	Date last_signoff;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getStation_type() {
		return station_type;
	}
	public void setStation_type(String station_type) {
		this.station_type = station_type;
	}
	public String getZmk() {
		return zmk;
	}
	public void setZmk(String zmk) {
		this.zmk = zmk;
	}
	public String getZpk() {
		return zpk;
	}
	public void setZpk(String zpk) {
		this.zpk = zpk;
	}
	public String getNew_zpk() {
		return new_zpk;
	}
	public void setNew_zpk(String new_zpk) {
		this.new_zpk = new_zpk;
	}
	public char getSigned_on() {
		return signed_on;
	}
	public void setSigned_on(char signed_on) {
		this.signed_on = signed_on;
	}
	public char getConnected() {
		return connected;
	}
	public void setConnected(char connected) {
		this.connected = connected;
	}
	public Date getLast_connect() {
		return last_connect;
	}
	public void setLast_connect(Date last_connect) {
		this.last_connect = last_connect;
	}
	public Date getLast_disconnect() {
		return last_disconnect;
	}
	public void setLast_disconnect(Date last_disconnect) {
		this.last_disconnect = last_disconnect;
	}
	public Date getLast_echo() {
		return last_echo;
	}
	public void setLast_echo(Date last_echo) {
		this.last_echo = last_echo;
	}
	public Date getLast_zpk_change() {
		return last_zpk_change;
	}
	public void setLast_zpk_change(Date last_zpk_change) {
		this.last_zpk_change = last_zpk_change;
	}
	public Date getLast_signon() {
		return last_signon;
	}
	public void setLast_signon(Date last_signon) {
		this.last_signon = last_signon;
	}
	public Date getLast_signoff() {
		return last_signoff;
	}
	public void setLast_signoff(Date last_signoff) {
		this.last_signoff = last_signoff;
	}
	
	
}
