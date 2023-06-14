package com.obs.sampleproject.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
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
