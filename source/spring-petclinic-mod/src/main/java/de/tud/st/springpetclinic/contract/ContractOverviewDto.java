package de.tud.st.springpetclinic.contract;

public class ContractOverviewDto {

	private final Long contractId;

	private final String contractName;

	private final String ownerName;

	private final String description;

	private final Double basePrice;

	private final int usedItemsCount;

	private final Double currentPriceSum;

	public ContractOverviewDto(Long contractId, String contractName, String ownerName, String description,
			Double basePrice, int usedItemsCount, Double currentPriceSum) {
		this.contractId = contractId;
		this.contractName = contractName;
		this.ownerName = ownerName;
		this.description = description;
		this.basePrice = basePrice;
		this.usedItemsCount = usedItemsCount;
		this.currentPriceSum = currentPriceSum;
	}

	public Long getContractId() {
		return contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getDescription() {
		return description;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public int getUsedItemsCount() {
		return usedItemsCount;
	}

	public Double getCurrentPriceSum() {
		return currentPriceSum;
	}

}