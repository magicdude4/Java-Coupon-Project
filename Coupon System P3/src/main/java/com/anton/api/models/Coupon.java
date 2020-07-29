package com.anton.api.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.anton.api.enums.CouponStatus;
import com.anton.api.enums.CouponType;

@Entity
@Table(name = "coupons")
public class Coupon {

	@Id
	@GeneratedValue
	@Column
	@NotNull
	private long id;
	@NotNull
	private String title;
	@NotNull
	private String message;
	@NotNull
	private double price;
	@NotNull
	private int amount;
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date start_date;
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date end_date;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) default 'OTHER'")
	private CouponType type = CouponType.OTHER;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) default 'SALE'")
	private CouponStatus status = CouponStatus.SALE;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public CouponStatus getStatus() {
		return status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}

	public Date getStart() {
		return start_date;
	}

	public void setStart(Date start) {
		this.start_date = start;
	}

	public Date getEnd() {
		return end_date;
	}

	public void setEnd(Date end) {
		this.end_date = end;
	}

	public Coupon(long id, String title, String message, double price, int amount, CouponType type,
			CouponStatus status) {
		this.id = id;
		this.title = title;
		this.message = message;
		this.price = price;
		this.amount = amount;
		this.type = type;
		this.status = status;
	}

}
