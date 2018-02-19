package com.adhocmaster.user.role;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class JSONRoleParser {

	public static final Logger logger = LoggerFactory.getLogger(JSONRoleParser.class);

	Path path;
	String content;
	ReadContext ctx;

	List<Role> roles;

	Map<Role, List<Capability>> roleCapabilities;

	/**
	 * 
	 * @param location
	 *            is relative to resources folder
	 * @throws URISyntaxException
	 */
	public JSONRoleParser(String location) throws URISyntaxException {

		// original

		// ClassLoader classLoader = getClass().getClassLoader();
		// URL url = classLoader.getResource(location);
		// logger.debug("got url for parsing capabilities:" + url.toURI());
		// path = Paths.get(url.toURI());
		// roles = new ArrayList<>();
		// roleCapabilities = new HashMap<>();

		// System.out.println("location" + location);

		// File file = null;
		try {
			// file = new ClassPathResource(location).getFile();
			// path = file.toPath();
			// roles = new ArrayList<>();
			// roleCapabilities = new HashMap<>();

			ClassPathResource resource = new ClassPathResource(location);
			InputStream resourceInputStream = resource.getInputStream();
			content = IOUtils.toString(resourceInputStream, StandardCharsets.UTF_8);
			roles = new ArrayList<>();
			roleCapabilities = new HashMap<>();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Role file not found");
		}

		/*
		 * final URI uri =
		 * getClass().getClassLoader().getResource(location).toURI();
		 * Map<String, String> env = new HashMap<>(); env.put("create", "true");
		 * try { FileSystem zipfs = FileSystems.newFileSystem(uri, env); path =
		 * Paths.get(uri); roles = new ArrayList<>(); roleCapabilities = new
		 * HashMap<>();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public List<Role> getRoles() {

		return roles;
	}

	public void setRoles(List<Role> roles) {

		this.roles = roles;
	}

	public Map<Role, List<Capability>> getRoleCapabilities() {

		return roleCapabilities;
	}

	public void setRoleCapabilities(Map<Role, List<Capability>> roleCapabilities) {

		this.roleCapabilities = roleCapabilities;
	}

	public void readLocation() throws IOException {

		content = new String(Files.readAllBytes(path));

	}

	public void parse() throws Exception {

		ctx = JsonPath.parse(content);

		if (null == ctx)
			throw new Exception("Json reader context null");

	}

	public void parseRoles() {

		List<String> stringRoles = ctx.read("$.roles");

		for (String name : stringRoles) {

			try {

				roles.add(Role.findByName(name));
				parseCapability(name);

			} catch (RoleNotFoundException e) {

				logger.error(name + " role not found while parsing");

			} catch (Exception e) {

				logger.error(e.getMessage());
			}

		}

	}

	public void parseCapability(String roleName) {

		try {

			List<String> stringCapabilities = ctx.read("$." + roleName);

			Role role = Role.findByName(roleName);

			logger.debug(stringCapabilities.toString());

			List<Capability> capabilities = new ArrayList<>();

			for (String capName : stringCapabilities) {

				try {

					Capability capability = Capability.findByName(capName);
					capabilities.add(capability);

				} catch (Exception e) {

					logger.error(e.getMessage());
				}

			}

			roleCapabilities.put(role, capabilities);

		} catch (Exception e) {

			logger.error(e.getMessage());

		}

	}

	public static Map<Role, List<Capability>> getRoleCapabilities(String resourceLocation) throws Exception {

		try {

			JSONRoleParser jsonRoleParser = new JSONRoleParser(resourceLocation);

			// jsonRoleParser.readLocation();

			jsonRoleParser.parse();
			jsonRoleParser.parseRoles();

			// System.out.println( jsonRoleParser.getRoles() );

			return jsonRoleParser.getRoleCapabilities();

		} catch (Exception e) {

			throw e;

		}
	}

}
