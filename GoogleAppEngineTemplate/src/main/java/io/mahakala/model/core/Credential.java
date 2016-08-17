/**
 * (C) Copyright 2010-2016. Nigel Cook. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mahakala.model.core;

import com.googlecode.objectify.annotation.Id;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;
import com.google.api.client.util.Base64;

@Entity
@Unindex
public class Credential {
	private static final Logger log = Logger.getLogger(Credential.class.getName());
	private @Id String name;
	private String value;
	
	protected Credential() { super();  }
	
	public Credential(String name, String value) {
		this.name = name;
		this.value = encrypt(value);
	}

	public String value() {
		return decrypt(this.value);
	}
	
	private String decrypt(String encrypted) {
		return decryptor(encrypted, System.getProperty("seed","elehp3N")+this.name);
	}

	private static String decryptor(String encrypted, String passwd) {
		try {
			byte[] key = (passwd).getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit

			SecretKeySpec spec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, spec);
			return new String(cipher.doFinal(Base64.decodeBase64(encrypted)));
		} catch (InvalidKeyException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		} catch (NoSuchPaddingException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		} catch (IllegalBlockSizeException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		} catch (BadPaddingException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "Decryption error", e);
			throw new IllegalArgumentException(e);
		}

	}


	private String encrypt(String str) {
		if(str == null) str = "";
		return encryptor(str, System.getProperty("seed","elehp3N")+this.name);
	}

	private static String encryptor(String str, String passwd) {
		try {
			byte[] key = (passwd).getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			SecretKeySpec spec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, spec);
			return new String(Base64.encodeBase64(cipher.doFinal(str
					.getBytes("UTF-8"))));
		} catch (InvalidKeyException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		} catch (NoSuchPaddingException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		} catch (IllegalBlockSizeException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		} catch (BadPaddingException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "Encryption error", e);
			throw new IllegalArgumentException(e);
		}

	}

}
