package com.test;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import com.model.User;

public class RunTest {
	@Test
	public void soft_assertion_assertj_test(){
		
	   String first = "Eddy";
	   String last = "Wong";
	   String email = "eddy@wong.com";
	   User user = new User(first, last, email);

	   SoftAssertions softly = new SoftAssertions();
	   softly.assertThat(user).isNotNull();
	   softly.assertThat(user.getFirst()).isEqualTo(first);
	   softly.assertThat(user.getLast()).isEqualTo(last);
	   softly.assertThat(user.getEmail()).isEqualTo(email);
	   softly.assertAll();
	}
}
