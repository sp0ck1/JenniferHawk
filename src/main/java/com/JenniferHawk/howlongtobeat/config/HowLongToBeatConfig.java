/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.JenniferHawk.howlongtobeat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.JenniferHawk.howlongtobeat.HowLongToBeatService;
import com.JenniferHawk.howlongtobeat.HowLongToBeatServiceDefaultImpl;

/**
 * Runtime configuration for services scoped for Howlongtobeat.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
public class HowLongToBeatConfig {

	@Bean(name = "howLongToBeatService")
	HowLongToBeatService createHowLongToBeatService() {
		return new HowLongToBeatServiceDefaultImpl();
	}
}
