/// *
// * Tests for ItemController - Task 1d
// * Package: de.tud.st.springpetclinic.controller
// */
//
// package de.tud.st.springpetclinic.controller;
//
// import de.tud.st.springpetclinic.medication.services.Contract;
// import de.tud.st.springpetclinic.medication.services.ContractHandler;
// import de.tud.st.springpetclinic.medication.services.ContractRepository;
// import de.tud.st.springpetclinic.system.storage.Item;
// import de.tud.st.springpetclinic.system.storage.Storage;
// import de.tud.st.springpetclinic.system.storage.UsedItem;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;
//
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;
//
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
/// **
// * Tests for ItemController. Tests both GET (show form) and POST (add item) endpoints.
// */
// @WebMvcTest(ItemController.class)
// class ItemControllerTests {
//
// @Autowired
// private MockMvc mockMvc;
//
// @MockBean
// private ContractRepository contractRepository;
//
// @MockBean
// private ContractHandler contractHandler;
//
// @MockBean
// private Storage storage;
//
// // Test data
// private Contract serviceBasic;
//
// private Item aspirin;
//
// private Item paracetamol;
//
// @BeforeEach
// void setup() {
// // Create fake items
// aspirin = new Item("Aspirin", 97, 12.5);
// aspirin.setId(1);
// paracetamol = new Item("Paracetamol", 50, 3.0);
// paracetamol.setId(2);
//
// // Create fake contract
// serviceBasic = new Contract("We take care of medication", 100.0, "Service Basic", new
/// ArrayList<>());
// serviceBasic.setId(1L);
//
// // Set up mock responses
// given(contractRepository.findById(1)).willReturn(Optional.of(serviceBasic));
// given(contractRepository.findById(999)).willReturn(Optional.empty());
// given(storage.findAll()).willReturn(Arrays.asList(aspirin, paracetamol));
//
// // When useItem is called with any item and contract, return the contract
// given(contractHandler.useItem(any(Item.class),
/// any(Contract.class))).willReturn(serviceBasic);
// }
//
// // ---------------------------------------------------------------
// // Tests for GET /contracts/addItem — Show item selection form
// // ---------------------------------------------------------------
//
// /**
// * Test 1: Show add item form with valid contractId. Expected: HTTP 200, correct view,
// * both contract and items in model.
// */
// @Test
// void testShowAddItemForm_ValidContract() throws Exception {
// mockMvc.perform(get("/contracts/addItem").param("contractId", "1"))
// .andExpect(status().isOk())
// .andExpect(view().name("contracts/addItem"))
// .andExpect(model().attributeExists("contract"))
// .andExpect(model().attributeExists("items"));
// }
//
// /**
// * Test 2: Show add item form - items list is populated from storage. Expected: items
// * attribute exists in model (Aspirin + Paracetamol).
// */
// @Test
// void testShowAddItemForm_ItemsLoadedFromStorage() throws Exception {
// mockMvc.perform(get("/contracts/addItem").param("contractId", "1"))
// .andExpect(status().isOk())
// .andExpect(model().attributeExists("items"));
// }
//
// /**
// * Test 3: Show add item form with INVALID contractId. Expected: page still loads
// * (orElse(null)), contract is null in model. Documents current behavior - ideally
// * should show error page.
// */
// @Test
// void testShowAddItemForm_InvalidContract() throws Exception {
// mockMvc.perform(get("/contracts/addItem").param("contractId", "999"))
// .andExpect(status().isOk())
// .andExpect(view().name("contracts/addItem"))
// .andExpect(model().attribute("contract", (Object) null));
// }
//
// // ---------------------------------------------------------------
// // Tests for POST /contracts/addItem — Add item to contract
// // ---------------------------------------------------------------
//
// /**
// * Test 4: Add item successfully — valid contractId and itemId. Expected: -
// * contractHandler.useItem() is called once - contractRepository.save() is called once
// * - Redirects back to /contracts.html
// */
// @Test
// void testAddItemToContract_Success() throws Exception {
// mockMvc.perform(post("/contracts/addItem").param("contractId", "1").param("itemId",
/// "1"))
// .andExpect(status().is3xxRedirection())
// .andExpect(view().name("redirect:/contracts.html"));
//
// // Verify useItem was called exactly once
// verify(contractHandler, times(1)).useItem(any(Item.class), any(Contract.class));
//
// // Verify save was called exactly once
// verify(contractRepository, times(1)).save(any(Contract.class));
// }
//
// /**
// * Test 5: Add item with INVALID contractId. Expected: - useItem() is NOT called
// * (contract is null) - save() is NOT called - Still redirects to /contracts.html (no
// * crash)
// */
// @Test
// void testAddItemToContract_InvalidContractId() throws Exception {
// mockMvc.perform(post("/contracts/addItem").param("contractId", "999").param("itemId",
/// "1"))
// .andExpect(status().is3xxRedirection())
// .andExpect(view().name("redirect:/contracts.html"));
//
// // Verify useItem was NOT called since contract is null
// verify(contractHandler, times(0)).useItem(any(Item.class), any(Contract.class));
// verify(contractRepository, times(0)).save(any(Contract.class));
// }
//
// /**
// * Test 6: Add item with INVALID itemId. Expected: - Item not found in storage (filter
// * finds nothing) - useItem() is NOT called - Still redirects to /contracts.html
// */
// @Test
// void testAddItemToContract_InvalidItemId() throws Exception {
// mockMvc.perform(post("/contracts/addItem").param("contractId", "1").param("itemId",
/// "999")) // item
// // 999
// // doesn't
// // exist
// .andExpect(status().is3xxRedirection())
// .andExpect(view().name("redirect:/contracts.html"));
//
// // Item not found so useItem should NOT be called
// verify(contractHandler, times(0)).useItem(any(Item.class), any(Contract.class));
// }
//
// /**
// * Test 7: Always redirects to contracts page after POST. Even if both contractId and
// * itemId are invalid, no crash.
// */
// @Test
// void testAddItemToContract_AlwaysRedirects() throws Exception {
// mockMvc.perform(post("/contracts/addItem").param("contractId", "999").param("itemId",
/// "999"))
// .andExpect(status().is3xxRedirection())
// .andExpect(view().name("redirect:/contracts.html"));
// }
//
// }
