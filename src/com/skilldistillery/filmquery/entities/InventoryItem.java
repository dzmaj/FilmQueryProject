package com.skilldistillery.filmquery.entities;

import java.time.LocalDateTime;

public class InventoryItem {
	private int id;
	private int filmId;
	private int storeId; 
	private String mediaCondition;
	private LocalDateTime lastUpdate;
}
