/*
 * Copyright (c) 2018-2025, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboot.kernel.cache.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Cache自动配置
 *
 * @author Chill
 */
@AutoConfiguration
@ConditionalOnClass(name = "net.sf.ehcache.CacheManager")
@ConditionalOnProperty(value = "blade.cache.enabled", havingValue = "true", matchIfMissing = true)
@EnableCaching
public class JCacheAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CacheManager cacheManager() {
		return new org.springframework.cache.concurrent.ConcurrentMapCacheManager("default");
	}

}