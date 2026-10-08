// package de.tud.st.springpetclinic.medication;
//
/// *
// * Tests for ContractHandler - Task 1a and Task 1d
// * Package: de.tud.st.springpetclinic.medication.services
// */
//
// import java.util.ArrayList;
// import java.util.List;
//
// import de.tud.st.springpetclinic.medication.services.Contract;
// import de.tud.st.springpetclinic.medication.services.ContractHandler;
// import de.tud.st.springpetclinic.medication.services.ContractRepository;
// import de.tud.st.springpetclinic.system.storage.Item;
// import de.tud.st.springpetclinic.system.storage.Storage;
// import de.tud.st.springpetclinic.system.storage.UsedItem;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.mock;
//
/// **
// * Unit tests for ContractHandler. These are pure Java tests - no Spring context needed.
// * ContractHandler has no dependencies so we just create it directly.
// */
// class ContractHandlerTests {
//
// private ContractRepository contractRepository;
//
// private Storage storage;
//
// private ContractHandler contractHandler;
//
// @BeforeEach
// void setUp() {
// contractRepository = mock(ContractRepository.class);
// storage = mock(Storage.class);
// contractHandler = new ContractHandler(contractRepository, storage);
// }
//
// // ---------------------------------------------------------------
// // Tests for calculateCurrentPriceSum() — Task 1a
// // ---------------------------------------------------------------
//
// /**
// * Test 1: Contract with NO used items. Expected: total price = basePrice only
// * <p>
// * Example: basePrice = 100.0, no items → total = 100.0
// */
//
// @Test
// void testCalculatePriceSum_NoUsedItems() {
// // ARRANGE — create a contract with empty usedItems list
// Contract contract = new Contract("description", 100.0, "Service Basic", new
// ArrayList<>());
//
// // ACT — call the method
// double result = contractHandler.calculateCurrentPriceSum(contract);
//
// // ASSERT — total should just be the base price
// assertEquals(100.0, result, "Price with no items should equal basePrice");
// }
//
// /**
// * Test 2: Contract with ONE used item. Expected: total price = basePrice + item price
// * <p>
// * Example: basePrice = 100.0, Aspirin = 12.5 → total = 112.5
// */
//
// @Test
// void testCalculatePriceSum_OneUsedItem() {
// // ARRANGE
// List<UsedItem> items = new ArrayList<>();
// items.add(new UsedItem("Aspirin", 12.5));
// Contract contract = new Contract("description", 100.0, "Service Basic", items);
//
// // ACT
// double result = contractHandler.calculateCurrentPriceSum(contract);
//
// // ASSERT
// assertEquals(112.5, result, "Price with one item should be basePrice + item price");
// }
//
// /**
// * Test 3: Contract with MULTIPLE used items. Expected: total price = basePrice + sum
// * of all item prices
// * <p>
// * Example: basePrice = 100.0, Aspirin = 12.5, Paracetamol = 3.0 → total = 115.5 This
// * matches the exact example shown in your presentation slides!
// */
// @Test
// void testCalculatePriceSum_MultipleUsedItems() {
// // ARRANGE
// List<UsedItem> items = new ArrayList<>();
// items.add(new UsedItem("Aspirin", 12.5));
// items.add(new UsedItem("Paracetamol", 3.0));
// Contract contract = new Contract("description", 100.0, "Service Basic", items);
//
// // ACT
// double result = contractHandler.calculateCurrentPriceSum(contract);
//
// // ASSERT — 100 + 12.5 + 3.0 = 115.5
// assertEquals(115.5, result, "Price with multiple items should be basePrice + all item
// prices");
// }
//
// /**
// * Test 4: Contract with NULL usedItems list. Expected: should not crash, return just
// * the basePrice
// * <p>
// * This tests the null check: if (contract.getUsedItems() != null) { ... }
// */
// @Test
// void testCalculatePriceSum_NullUsedItems() {
// // ARRANGE — create a contract where usedItems is null
// Contract contract = new Contract("description", 100.0, "Service Basic", null);
//
// // ACT
// double result = contractHandler.calculateCurrentPriceSum(contract);
//
// // ASSERT — should not throw NullPointerException, just return basePrice
// assertEquals(100.0, result, "Price with null items should equal basePrice without
// crashing");
// }
//
// /**
// * Test 5: Contract with zero basePrice and items. Expected: total = only the item
// * prices
// */
// @Test
// void testCalculatePriceSum_ZeroBasePrice() {
// // ARRANGE
// List<UsedItem> items = new ArrayList<>();
// items.add(new UsedItem("Aspirin", 12.5));
// Contract contract = new Contract("description", 0.0, "Free Service", items);
//
// // ACT
// double result = contractHandler.calculateCurrentPriceSum(contract);
//
// // ASSERT
// assertEquals(12.5, result, "Price with zero base should equal sum of item prices
// only");
// }
//
// // ---------------------------------------------------------------
// // Tests for useItem() — Task 1d
// // ---------------------------------------------------------------
//
// /**
// * Test 6: useItem() with an item IN stock. Expected: - Contract gets a new UsedItem -
// * Item stock is reduced by 1 - Method returns the contract (not null)
// */
// @Test
// void testUseItem_ItemInStock() {
// // ARRANGE
// Item item = new Item("Aspirin", 10, 12.5); // 10 in stock
// List<UsedItem> usedItems = new ArrayList<>();
// Contract contract = new Contract("description", 100.0, "Service Basic", usedItems);
//
// // ACT
// Contract result = contractHandler.useItem(item, contract);
//
// // ASSERT
// assertNotNull(result, "useItem should return the contract when item is in stock");
// assertEquals(1, contract.getUsedItems().size(), "Contract should have 1 UsedItem after
// adding");
// assertEquals("Aspirin", contract.getUsedItems().get(0).getDescription(),
// "UsedItem should have correct description");
// assertEquals(12.5, contract.getUsedItems().get(0).getPrice(), "UsedItem should have
// correct price");
// assertEquals(9, item.getInStock(), "Item stock should be reduced by 1");
// }
//
// /**
// * Test 7: useItem() with an item OUT of stock. Expected: - Method returns null -
// * Contract gets NO new UsedItem - Item stock stays the same (0)
// */
// @Test
// void testUseItem_ItemOutOfStock() {
// // ARRANGE
// Item item = new Item("Aspirin", 0, 12.5); // 0 in stock — out of stock!
// List<UsedItem> usedItems = new ArrayList<>();
// Contract contract = new Contract("description", 100.0, "Service Basic", usedItems);
//
// // ACT
// Contract result = contractHandler.useItem(item, contract);
//
// // ASSERT
// assertNull(result, "useItem should return null when item is out of stock");
// assertEquals(0, contract.getUsedItems().size(), "Contract should have no UsedItems
// added");
// assertEquals(0, item.getInStock(), "Item stock should remain 0");
// }
//
// /**
// * Test 8: useItem() adds CORRECT price and description to UsedItem. Expected:
// * UsedItem is a snapshot — copies item's values exactly.
// */
// @Test
// void testUseItem_UsedItemCopiesCorrectValues() {
// // ARRANGE
// Item item = new Item("Paracetamol", 50, 3.0);
// List<UsedItem> usedItems = new ArrayList<>();
// Contract contract = new Contract("description", 500.0, "Service Premium", usedItems);
//
// // ACT
// contractHandler.useItem(item, contract);
//
// // ASSERT
// UsedItem addedItem = contract.getUsedItems().get(0);
// assertEquals("Paracetamol", addedItem.getDescription(), "UsedItem description should
// match Item description");
// assertEquals(3.0, addedItem.getPrice(), "UsedItem price should match Item price");
// }
//
// /**
// * Test 9: useItem() called TWICE — stock reduces by 2, contract has 2 UsedItems.
// */
// @Test
// void testUseItem_CalledTwice() {
// // ARRANGE
// Item item = new Item("Aspirin", 5, 12.5);
// List<UsedItem> usedItems = new ArrayList<>();
// Contract contract = new Contract("description", 100.0, "Service Basic", usedItems);
//
// // ACT — add the same item twice
// contractHandler.useItem(item, contract);
// contractHandler.useItem(item, contract);
//
// // ASSERT
// assertEquals(2, contract.getUsedItems().size(), "Contract should have 2 UsedItems");
// assertEquals(3, item.getInStock(), "Stock should be reduced by 2 (5 - 2 = 3)");
// }
//
// /**
// * Test 10: useItem() with exactly 1 item in stock. Expected: first call succeeds,
// * second call returns null (out of stock).
// */
// @Test
// void testUseItem_LastItemInStock() {
// // ARRANGE
// Item item = new Item("Aspirin", 1, 12.5); // only 1 left!
// List<UsedItem> usedItems = new ArrayList<>();
// Contract contract = new Contract("description", 100.0, "Service Basic", usedItems);
//
// // ACT
// Contract firstResult = contractHandler.useItem(item, contract); // should succeed
// Contract secondResult = contractHandler.useItem(item, contract); // should fail -
// // out of
// // stock
//
// // ASSERT
// assertNotNull(firstResult, "First call should succeed");
// assertNull(secondResult, "Second call should fail - now out of stock");
// assertEquals(1, contract.getUsedItems().size(), "Only 1 UsedItem should have been
// added");
// assertEquals(0, item.getInStock(), "Stock should be 0 after using last item");
// }
//
// }
