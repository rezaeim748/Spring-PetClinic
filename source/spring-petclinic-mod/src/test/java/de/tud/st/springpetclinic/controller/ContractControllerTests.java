// package de.tud.st.springpetclinic.controller;
//
/// *
// * Tests for ContractController - Task 1a and Task 1b
// * Package: de.tud.st.springpetclinic.controller
// */
//
// import de.tud.st.springpetclinic.medication.services.Contract;
// import de.tud.st.springpetclinic.medication.services.ContractHandler;
// import de.tud.st.springpetclinic.medication.services.ContractRepository;
// import de.tud.st.springpetclinic.owner.owner.Owner;
// import de.tud.st.springpetclinic.owner.owner.OwnerRepository;
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
// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
/// **
// * Tests for ContractController using MockMvc.
// *
// * @WebMvcTest loads only the web layer (controllers + MockMvc). All dependencies
// * (repositories, handlers) are replaced with @MockBean fakes. No real database is used.
// */
// @WebMvcTest(ContractController.class)
// class ContractControllerTests {
//
// @Autowired
// private MockMvc mockMvc;
//
// // Mock all dependencies that ContractController needs
// @MockBean
// private ContractRepository contractRepository;
//
// @MockBean
// private ContractHandler contractHandler;
//
// @MockBean
// private Storage storage;
//
// @MockBean
// private OwnerRepository ownerRepository;
//
// // Test data - reused across tests
// private Owner george;
//
// private Contract serviceBasic;
//
// @BeforeEach
// void setup() {
// // Create a fake UsedItem
// UsedItem aspirin = new UsedItem("Aspirin", 12.5);
// List<UsedItem> usedItems = new ArrayList<>();
// usedItems.add(aspirin);
//
// // Create a fake Contract
// serviceBasic = new Contract("We take care of medication", 100.0, "Service Basic",
// usedItems);
// serviceBasic.setId(1L);
//
// // Create a fake Owner with the contract
// george = new Owner();
// george.setId(1);
// george.setFirstName("George");
// george.setLastName("Franklin");
// george.setAddress("110 W. Liberty St.");
// george.setCity("Madison");
// george.setTelephone("6085551023");
// george.bookedServices = new ArrayList<>();
// george.bookedServices.add(serviceBasic);
//
// // Tell mocks what to return when called
// given(ownerRepository.findAllNotPaged()).willReturn(Arrays.asList(george));
// given(contractHandler.calculateCurrentPriceSum(serviceBasic)).willReturn(112.5); // 100
// // +
// // 12.5
// given(contractRepository.findById(1)).willReturn(Optional.of(serviceBasic));
// given(contractRepository.findById(999)).willReturn(Optional.empty()); // non-existent
// }
//
// // ---------------------------------------------------------------
// // Tests for GET /contracts.html — Task 1a
// // ---------------------------------------------------------------
//
// /**
// * Test 1: Contracts page loads successfully. Expected: HTTP 200, correct view name,
// * listContracts in model
// */
// @Test
// void testShowContracts_PageLoadsSuccessfully() throws Exception {
// mockMvc.perform(get("/contracts.html"))
// .andExpect(status().isOk())
// .andExpect(view().name("contracts/contractsList"))
// .andExpect(model().attributeExists("listContracts"));
// }
//
// /**
// * Test 2: Contracts page includes contractPrices in the model. Expected:
// * contractPrices map is sent to the view so HTML can look up prices.
// */
// @Test
// void testShowContracts_ContractPricesInModel() throws Exception {
// mockMvc.perform(get("/contracts.html"))
// .andExpect(status().isOk())
// .andExpect(model().attributeExists("contractPrices"));
// }
//
// /**
// * Test 3: Contracts page returns HTTP 200 (not 404 or 500). Basic sanity check - the
// * endpoint exists and doesn't crash.
// */
// @Test
// void testShowContracts_Returns200() throws Exception {
// mockMvc.perform(get("/contracts.html")).andExpect(status().isOk());
// }
//
// // ---------------------------------------------------------------
// // Tests for GET /contracts/details.html — Task 1b
// // ---------------------------------------------------------------
//
// /**
// * Test 4: Contract details page loads with a valid contractId. Expected: HTTP 200,
// * correct view, contract in model
// */
// @Test
// void testShowContractDetails_ValidContractId() throws Exception {
// mockMvc.perform(get("/contracts/details.html").param("contractId", "1"))
// .andExpect(status().isOk())
// .andExpect(view().name("contracts/contractDetails"))
// .andExpect(model().attributeExists("contract"));
// }
//
// /**
// * Test 5: Contract details page - missing contractId param. Expected: HTTP 400 Bad
// * Request (Spring rejects missing required param).
// */
// @Test
// void testShowContractDetails_MissingContractId_Returns400() throws Exception {
// mockMvc.perform(get("/contracts/details.html")).andExpect(status().isBadRequest());
// }
//
// }
