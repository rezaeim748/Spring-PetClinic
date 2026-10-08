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

package de.tud.st.springpetclinic.medication.services;

import org.springframework.stereotype.Service;
import de.tud.st.springpetclinic.system.storage.UsedItem;

import de.tud.st.springpetclinic.owner.owner.Owner;
import de.tud.st.springpetclinic.system.storage.Item;
import de.tud.st.springpetclinic.system.storage.Storage;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * @author Karl Kegel
 */
@Service
public class ContractHandler {

	private final ContractRepository contractRepository;

	private final Storage storage;

	public ContractHandler(ContractRepository contractRepository, Storage storage) {
		this.contractRepository = contractRepository;
		this.storage = storage;
	}

	public Contract startContract(Owner owner, Contract contract) {
		// TODO
		if (owner.bookedServices == null) {
			owner.bookedServices = new java.util.ArrayList<>();
		}
		owner.bookedServices.add(contract);
		return contract;
	}

	/**
	 * Task 1d: Prepares the model for the "Add Item to Contract" form by loading the
	 * contract and all available storage items.
	 */
	public void prepareAddItemForm(int contractId, Model model) {
		model.addAttribute("contract", contractRepository.findById(contractId).orElse(null));
		model.addAttribute("items", storage.findAll());
	}

	/**
	 * Task 1d: Looks up the contract and item by their IDs, adds the item to the contract
	 * as a UsedItem, and persists the change. Returns the updated contract, or null if
	 * the contract or item could not be found or the item is out of stock.
	 */
	public Contract addItemToContract(int contractId, int itemId) {
		Contract contract = contractRepository.findById(contractId).orElse(null);
		Item item = storage.findAll().stream().filter(i -> i.getId() == itemId).findFirst().orElse(null);

		if (contract == null || item == null) {
			return null;
		}

		Contract updated = useItem(item, contract);
		if (updated == null) {
			return null;
		}

		contractRepository.save(updated);
		return updated;
	}

	/**
	 * Task 1d: Adds an item from storage to a contract as a UsedItem. Decrements stock
	 * count and creates a new UsedItem linked to the contract.
	 */
	public Contract useItem(Item item, Contract contract) {
		if (item.getInStock() <= 0) {
			System.out.println("Item not available: " + item.getDescription());
			return null;
		}
		item.setInStock(item.getInStock() - 1);
		UsedItem usedItem = new UsedItem(item.getDescription(), item.getPrice());
		contract.getUsedItems().add(usedItem);
		return contract;
	}

	public double calculateCurrentPriceSum(Contract contract) {
		// TODO
		// Total price = contract base price + all used item prices
		// Task-a:
		double totalPrice = contract.getBasePrice();
		if (contract.getUsedItems() != null) {
			for (var usedItem : contract.getUsedItems()) {
				totalPrice += usedItem.getPrice();
			}
		}
		return totalPrice;
	}

}