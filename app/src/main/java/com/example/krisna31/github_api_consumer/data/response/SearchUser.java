package com.example.krisna31.github_api_consumer.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUser {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<SearchUserItem> users;

	public int getTotalCount() {
		return totalCount;
	}

	public boolean isIncompleteResults() {
		return incompleteResults;
	}

	public List<SearchUserItem> getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return
				"Response{" +
						"total_count = '" + totalCount + '\'' +
						",incomplete_results = '" + incompleteResults + '\'' +
						",items = '" + users + '\'' +
						"}";
	}
}