package com.rest.exercise.model;

import java.io.Serializable;
import java.util.List;

public class Envelope<T extends Serializable> {

	private T data = null;
	private List<String> errors = null;

	public Envelope() {
		super();
	}
	public Envelope(T data, List<String> errors) {
		this.data = data;
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public static class EnvelopeBuilder {

		Envelope envelope;

		public EnvelopeBuilder() {
			envelope = new Envelope<>();
		}

		public EnvelopeBuilder(Envelope envlope) {
			envelope = envlope;
		}

		public <T extends Serializable> EnvelopeBuilder withData(T data) {
			envelope.data = data;
			return this;
		}
		public EnvelopeBuilder withErrors(List<String> errors) {
			envelope.errors = errors;
			return this;
		}

		public <T extends Serializable> Envelope<T> build() {
			return envelope;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Envelope[data=");
		builder.append(data.toString());
		builder.append("]");
		return builder.toString();
	}
}