import com.task.Trade;
import com.task.TradeStore;



import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MyTradeTests {

	@InjectMocks
	TradeStore tradeStore;

	@Before
	public void setUp() throws Exception {
		System.out.println("Set Up @BeforeEach");
		Map<String, List<Trade>> tradeStoreMap = new HashMap<String, List<Trade>>();
		LocalDate maturityDate = LocalDate.now();
		tradeStoreMap.put("T1", Arrays.asList(new Trade("T1", 1, "CP-1", "B1", maturityDate, LocalDate.now(), "N"),
				new Trade("T1", 2, "CP-2", "B2", maturityDate, LocalDate.now(), "Y")));
		tradeStoreMap.put("T2", Arrays.asList(new Trade("T2", 1, "CP-3", "B2", maturityDate, LocalDate.now(), "Y")));
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void versionIdTest_False() {
		LocalDate maturityDate = LocalDate.now();
		Trade t = new Trade("T1", 1, "CP-1", "B1", maturityDate, LocalDate.now(), "N");
		assertEquals(false, tradeStore.versionIdValidation(t));
	}

	@Test
	public void versionIdTest_True() {
		LocalDate maturityDate = LocalDate.now();
		Trade t = new Trade("T1", 3, "CP-1", "B1", maturityDate, LocalDate.now(), "N");
		assertEquals(true, tradeStore.versionIdValidation(t));
	}

	@Test
	public void maturityDate_False() {
		LocalDate maturityDate = LocalDate.parse("2021-08-18");
		assertEquals(false, tradeStore.maturityDateValidation(maturityDate));
	}

	@Test
	public void maturityDate_True() {
		LocalDate maturityDate = LocalDate.now();
		assertEquals(true, TradeStore.maturityDateValidation(maturityDate));
	}

	@Test
	public void processTradeTest() {
		LocalDate maturityDate = LocalDate.now();
		Trade trade = new Trade("T1", 2, "CP-2", "B2", maturityDate, LocalDate.now(), "Y");
		tradeStore.versionIdValidation(trade);
		tradeStore.maturityDateValidation(maturityDate);
		tradeStore.processTrade(trade);
	}

	@Test(expected = RuntimeException.class)
	public void processTrade_versionIdTest_False() {
		LocalDate maturityDate = LocalDate.now();
		Trade trade = new Trade("T1", 1, "CP-1", "B1", maturityDate, LocalDate.now(), "N");
		tradeStore.processTrade(trade);
	}

	@Test(expected = RuntimeException.class)
	public void processTrade_maturityDate_False() {
		LocalDate maturityDate = LocalDate.parse("2021-08-17");
		Trade trade = new Trade("T1", 1, "CP-1", "B1", maturityDate, LocalDate.now(), "N");
		tradeStore.processTrade(trade);
	}
	
	@Test
	public void main() {
		LocalDate maturityDate = LocalDate.now();
		Trade trade = new Trade("T1", 2, "CP-2", "B2", maturityDate, LocalDate.now(), "Y");
		String args[]= {"data"};
		tradeStore.main(args);
	}
}
