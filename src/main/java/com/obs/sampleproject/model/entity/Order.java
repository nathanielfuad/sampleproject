package com.obs.sampleproject.model.entity;


import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "\"ORDER\"")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String orderNo;
	@ManyToOne(optional = false)
	private Item item;
	private Integer qty;

}
