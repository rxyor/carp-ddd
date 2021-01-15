/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rxyor.carp.search.api.consts;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019-04-07 Sun 17:02:34
 * @since 1.0.0
 */
public interface CryptoConst {

    /**
     * 加密相关
     */
    interface Crypt {

        /**
         * 安全标识KEY
         */
        String SECURITY_KEY = "crypto_api_sign";

        /**
         * {bcrypt} 加密的特征码
         */
        String BCRYPT = "{bcrypt}";
    }


}
