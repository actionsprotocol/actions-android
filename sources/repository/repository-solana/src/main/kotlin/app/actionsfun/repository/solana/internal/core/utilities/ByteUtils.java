/*
 * Copyright by the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.actionsfun.repository.solana.internal.core.utilities;

import java.math.BigInteger;

/**
 * Utility methods for bit, byte, and integer manipulation and conversion. Most of these were moved here
 * from {@code org.bitcoinj.core.Utils}.
 */
public class ByteUtils {
    /**
     *
     * @param bytes to convert into a BigInteger
     * @return the converted BigInteger
     */
    public static BigInteger bytesToBigInteger(byte[] bytes) {
        return new BigInteger(1, bytes);
    }
}
