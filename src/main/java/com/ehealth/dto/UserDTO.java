package com.ehealth.dto;
 
import java.time.LocalDateTime;

/**
 * @author nagaraj
 *
 */
 
public class UserDTO {

	private String id;
	private String userId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Long phoneNumber;
	private Address address;
	private Boolean isAdmin;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
 
	public static class Address {

		private String line1;
		private String line2;
		private String landmark;
		private String state;
		private String city;
		private int pinCode;
		
	}

}