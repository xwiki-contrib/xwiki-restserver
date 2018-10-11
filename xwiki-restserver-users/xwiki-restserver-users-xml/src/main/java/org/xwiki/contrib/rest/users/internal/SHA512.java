/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.rest.users.internal;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.xwiki.contrib.rest.XWikiRestServerException;

/**
 * Utility class to perform SHA512 checksums.
 *
 * @version $Id: $
 */
public final class SHA512
{
    private static final String UTF_8 = "UTF-8";

    private static final String SHA_512 = "SHA-512";

    /**
     * It is forbidden to create an instance of that class.
     */
    private SHA512()
    {
    }

    /**
     * Hash the given string with the given salt.
     * @param toHash string to hash
     * @param salt the salt
     * @return a SHA-512 hashed string
     * @throws XWikiRestServerException if an error occurs
     */
    public static String hash(String toHash, String salt) throws XWikiRestServerException
    {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_512);
            md.update(salt.getBytes(UTF_8));
            byte[] bytes = md.digest(toHash.getBytes(UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new XWikiRestServerException("Failed to compute a SHA512 hash.", e);
        }
    }
}
