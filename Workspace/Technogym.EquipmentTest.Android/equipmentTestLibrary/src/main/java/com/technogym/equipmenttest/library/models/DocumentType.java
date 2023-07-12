package com.technogym.equipmenttest.library.models;

public enum DocumentType {
	PDF("https://docs.google.com/gview?embedded=true&url=%s");

	private final String mPlaceholder;

	private DocumentType(final String placeholder) {
		this.mPlaceholder = placeholder;
	}

	public String getUrlPlaceholder() {
		return this.mPlaceholder;
	}
}
