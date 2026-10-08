/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tud.st.springpetclinic.controller;

import org.springframework.web.bind.annotation.PostMapping;

import de.tud.st.springpetclinic.medication.services.ContractHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Karl Kegel
 */
@Controller
public class ItemController {

	private final ContractHandler contractHandler;

	// Constructor is used for injection
	public ItemController(ContractHandler contractHandler) {
		this.contractHandler = contractHandler;
	}

	/*
	 * Task 1.d Add an item to a contract. Use ContractHandler.useItem(...)
	 */

	// Show form with all available storage items for a contract
	@GetMapping("/contracts/addItem")
	public String showAddItemForm(@RequestParam int contractId, Model model) {
		contractHandler.prepareAddItemForm(contractId, model);
		return "contracts/addItem";
	}

	// Add selected item to contract — delegates all logic to ContractHandler
	@PostMapping("/contracts/addItem")
	public String addItemToContract(@RequestParam int contractId, @RequestParam int itemId) {
		contractHandler.addItemToContract(contractId, itemId);
		return "redirect:/contracts.html";
	}

}