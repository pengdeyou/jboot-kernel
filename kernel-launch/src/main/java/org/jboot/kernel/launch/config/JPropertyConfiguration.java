/**
 * Copyright (c) 2019-2029, DreamLu 卢春梦 (596392912@qq.com & www.dreamlu.net).
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

package org.jboot.kernel.launch.config;

import org.jboot.kernel.launch.props.JProperties;
import org.jboot.kernel.launch.props.JPropertySourcePostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * blade property config
 *
 * @author L.cm
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(JProperties.class)
public class JPropertyConfiguration {

	@Bean
	public JPropertySourcePostProcessor bladePropertySourcePostProcessor() {
		return new JPropertySourcePostProcessor();
	}

}
