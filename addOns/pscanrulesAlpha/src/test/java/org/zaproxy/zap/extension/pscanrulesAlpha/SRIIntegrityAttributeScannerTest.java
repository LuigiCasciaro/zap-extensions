package org.zaproxy.zap.extension.pscanrulesAlpha;

import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SRIIntegrityAttributeScannerTest
    extends PassiveScannerTest<SRIIntegrityAttributeScanner> {

  // Test cases
  // Without attribute but in the current domain => No alert
  // Without attribute but in a different domain => Alert
  // script in body?

  @Test
  public void shouldNotRaiseAlertGivenIntegrityAttributeIsPresentInLinkTag() {
    // Given a HTML page with link tag containing an integrity attribute
    HttpMessage msg = new HttpMessage();
    // from https://www.w3.org/TR/SRI/#use-casesexamples
    msg.setResponseBody(
        "<html><head><link rel=\"stylesheet\" href=\"https://site53.example.net/style.css\"\n"
            + "      integrity=\"sha384-+/M6kredJcxdsqkczBUjMLvqyHb1K/JThDXWsBVxMEeZHEaMKEOEct339VItX1zB\"\n"
            + "      crossorigin=\"anonymous\"></head><body></body></html>");

    // When the page is scanned
    rule.scanHttpResponseReceive(msg, -1, createSource(msg));

    // Then no alert should be raised
    assertThat(alertsRaised.size(), equalTo(0));
  }

  @Test
  public void shouldNotRaiseAlertGivenIntegrityAttributeIsPresentInScriptTag() {
    // Given a HTML page with link tag containing an integrity attribute
    HttpMessage msg = new HttpMessage();
    // from https://www.w3.org/TR/SRI/#use-casesexamples
    msg.setResponseBody(
        "<html><head><script src=\"https://analytics-r-us.example.com/v1.0/include.js\"\n"
            + "        integrity=\"sha384-MBO5IDfYaE6c6Aao94oZrIOiC6CGiSN2n4QUbHNPhzk5Xhm0djZLQqTpL0HzTUxk\"\n"
            + "        crossorigin=\"anonymous\"></script></head><body></body></html>");

    // When the page is scanned
    rule.scanHttpResponseReceive(msg, -1, createSource(msg));

    // Then no alert should be raised
    assertThat(alertsRaised.size(), equalTo(0));
  }

  @Override
  protected SRIIntegrityAttributeScanner createScanner() {
    return new SRIIntegrityAttributeScanner();
  }
}
