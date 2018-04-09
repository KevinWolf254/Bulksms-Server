package co.ke.bigfootke.app.pojos;

import java.util.List;

import co.ke.bigfootke.app.entities.Client;

public class ClientPagedData {
	private List<Client> client;
	private Page page;
	
	public ClientPagedData() {
	}

	public ClientPagedData(List<Client> client, Page page) {
		this.client = client;
		this.page = page;
	}

	public List<Client> getClient() {
		return client;
	}

	public void setClient(List<Client> client) {
		this.client = client;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
