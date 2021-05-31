package com.classes.model.bean.products;

import java.io.Serializable;
import java.util.Objects;

public class ImageBean implements Serializable{

		private static final long serialVersionUID = 1L;
		
		private byte[] image;
		private int codId;
		
		public ImageBean() {
			image = new byte[1024*20];
			codId = -1;
		}

		public byte[] getImage() {
			return image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}

		public int getCodId() {
			return codId;
		}

		public void setCodId(int codId) {
			this.codId = codId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ImageBean that = (ImageBean) o;
			return  image.equals(that.image) &&
					codId == that.codId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(image, codId);
		}
}

