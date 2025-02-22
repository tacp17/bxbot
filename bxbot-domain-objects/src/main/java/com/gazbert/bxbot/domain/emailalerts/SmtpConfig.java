/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Gareth Jon Lynch
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

package com.gazbert.bxbot.domain.emailalerts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

/**
 * Domain object representing the SMTP config used for Email Alerts.
 *
 * @author gazbert
 */
@Data
@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
public class SmtpConfig {

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The SMTP hostname.")
  private String host;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The SMTP TLS port.")
  @Positive(message = "Port must be positive integer")
  private int tlsPort;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "The sender email account name.")
  private String accountUsername;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "The sender email account password.")
  @ToString.Exclude
  private String accountPassword;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The email From address.")
  @Email(message = "From Address must be a valid email address")
  private String fromAddress;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The email To address.")
  @Email(message = "To Address must be a valid email address")
  private String toAddress;

  /** Creates a new SmtpConfig. */
  public SmtpConfig() {
    // noimpl
  }

  /**
   * Creates a new SmtpConfig.
   *
   * @param host the SMTP host.
   * @param tlsPort the TLS port to use.
   * @param accountUsername the SMTP account name.
   * @param accountPassword the SMTP account password.
   * @param fromAddress the email From address.
   * @param toAddress the email To address.
   */
  public SmtpConfig(
      String host,
      int tlsPort,
      String accountUsername,
      String accountPassword,
      String fromAddress,
      String toAddress) {

    this.host = host;
    this.tlsPort = tlsPort;
    this.accountUsername = accountUsername;
    this.accountPassword = accountPassword;
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
  }
}
