/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Gareth Jon Lynch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.gazbert.bxbot.exchanges;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gazbert.bxbot.exchange.api.AuthenticationConfig;
import com.gazbert.bxbot.exchange.api.ExchangeAdapter;
import com.gazbert.bxbot.exchange.api.ExchangeConfig;
import com.gazbert.bxbot.exchange.api.NetworkConfig;
import com.gazbert.bxbot.exchange.api.OtherConfig;
import com.gazbert.bxbot.trading.api.MarketOrderBook;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic integration testing with Coinbase Advanced Trade exchange.
 *
 * @author gazbert
 */
public class CoinbaseIT {

  private static final String MARKET_ID = "BTC-GBP";

  private static final String PASSPHRASE = "lePassPhrase";
  private static final String KEY = "key123";
  private static final String SECRET = "notGonnaTellYa";
  private static final List<Integer> nonFatalNetworkErrorCodes = Arrays.asList(502, 503, 504);
  private static final List<String> nonFatalNetworkErrorMessages =
      Arrays.asList(
          "Connection refused",
          "Connection reset",
          "Remote host closed connection during handshake");

  private ExchangeConfig exchangeConfig;
  private AuthenticationConfig authenticationConfig;
  private NetworkConfig networkConfig;
  private OtherConfig otherConfig;

  /** Create some exchange config - the TradingEngine would normally do this. */
  @Before
  public void setupForEachTest() {
    authenticationConfig = createMock(AuthenticationConfig.class);

    networkConfig = createMock(NetworkConfig.class);
    expect(networkConfig.getConnectionTimeout()).andReturn(30);
    expect(networkConfig.getNonFatalErrorCodes()).andReturn(nonFatalNetworkErrorCodes);
    expect(networkConfig.getNonFatalErrorMessages()).andReturn(nonFatalNetworkErrorMessages);

    otherConfig = createMock(OtherConfig.class);
    expect(otherConfig.getItem("buy-fee")).andReturn("0.25");
    expect(otherConfig.getItem("sell-fee")).andReturn("0.25");
    expect(otherConfig.getItem("time-server-bias")).andReturn("1");

    exchangeConfig = createMock(ExchangeConfig.class);
    expect(exchangeConfig.getNetworkConfig()).andReturn(networkConfig);
    expect(exchangeConfig.getOtherConfig()).andReturn(otherConfig);
  }

  @Test
  public void testPublicApiCalls() throws Exception {
    replay(authenticationConfig, networkConfig, otherConfig, exchangeConfig);

    final ExchangeAdapter exchangeAdapter = new CoinbaseExchangeAdapter();
    exchangeAdapter.init(exchangeConfig);

    final MarketOrderBook orderBook = exchangeAdapter.getMarketOrders(MARKET_ID);
    assertEquals(100, orderBook.getBuyOrders().size());
    assertEquals(100, orderBook.getSellOrders().size());

    verify(authenticationConfig, networkConfig, otherConfig, exchangeConfig);
  }
}
