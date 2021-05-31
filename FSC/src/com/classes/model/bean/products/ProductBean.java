package com.classes.model.bean.products;

import java.io.Serializable;
import java.util.Objects;

public class ProductBean implements Serializable{

		private static final long serialVersionUID = 1L;
		
		private int code;
		private String name;
		private String description;
		private int price;
		private int quantity;
		
		public ProductBean() {
			code = -1;
			name = "";
			description = "";
			price = 0;
			quantity = 0;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		@Override
		public String toString() {
			return name+" ("+code+") "+price+", "+quantity+", "+description;
		}

		public boolean isEmpty() {
			return code == -1;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ProductBean that = (ProductBean) o;
			return code == that.code &&
					price == that.price &&
					quantity == that.quantity &&
					name.equals(that.name) &&
					description.equals(that.description);
		}

		@Override
		public int hashCode() {
			return Objects.hash(code, name, description, price, quantity);
		}
}
