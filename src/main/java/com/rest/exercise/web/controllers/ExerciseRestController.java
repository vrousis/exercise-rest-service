package com.rest.exercise.web.controllers;

import java.util.ArrayList;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.exercise.model.Envelope;
import com.rest.exercise.service.ItemService;
import com.rest.exercise.model.Item;
import com.rest.exercise.web.RequestHeaders;

@RestController
@RequestMapping("/rest")
public class ExerciseRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseRestController.class);

	public static final String RETRIEVE_ITEM = "/item/{itemId}";
	public static final String RETRIEVE_ITEM_REGEX = "/**/item/**";
	public static final String RETRIEVE_ITEM_FOR_CUSTOMER = "/item";
	public static final String RETRIEVE_ITEM_FOR_CUSTOMER_REGEX = "/**/item";
	public static final String SET_ITEM_FOR_CUSTOMER = "/item";
	public static final String SET_ITEM_FOR_CUSTOMER_REGEX = "/**/item";

    private ItemService itemService;
    private final RequestHeaders requestHeaders;

    @Autowired
	public ExerciseRestController(
			ItemService itemService,
			RequestHeaders requestHeaders
	) {
		this.itemService = Objects.requireNonNull(itemService);
        this.requestHeaders = Objects.requireNonNull(requestHeaders);
	}

	/**
	 * Retrieve an item
	 *
	 * @param itemId
	 * @return
	 */
	@RequestMapping(
			value = RETRIEVE_ITEM,
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Envelope<Item> retrieveItem(@PathVariable String itemId)
	{
		Item item = itemService.findByItemId(itemId);
		Envelope<Item> envelope = new Envelope.EnvelopeBuilder()
				.withData(item)
				.build();
		return envelope;
	}
	
	/**
	 * Retrieve items for a customer
	 *
	 */
	@RequestMapping(
			value = RETRIEVE_ITEM_FOR_CUSTOMER,
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Envelope<ArrayList<Item>> retrieveItemsForCustomer()
	{
		ArrayList<Item> items = new ArrayList<>(itemService.findByCustomerId(requestHeaders.getXToken()));
		return new Envelope.EnvelopeBuilder()
				.withData(items)
				.build();
	}

	/**
	 * Save an item for a customer
	 *
	 */
	@RequestMapping(
			value = SET_ITEM_FOR_CUSTOMER,
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Envelope<Item> setItemForCustomer(
			@RequestBody Item item)
	{
		item.setCustomerId(requestHeaders.getXToken());
		Item itemResponse = itemService.save(item);
		return new Envelope.EnvelopeBuilder()
				.withData(itemResponse)
				.build();

	}

}
