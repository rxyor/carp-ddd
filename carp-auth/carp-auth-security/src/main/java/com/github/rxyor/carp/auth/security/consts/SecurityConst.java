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

package com.github.rxyor.carp.auth.security.consts;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019-04-07 Sun 17:02:34
 * @since 1.0.0
 */
public interface SecurityConst {

    interface TokenAccess {

        String PERMIT_ALL = "permitAll()";
        String DENY_ALL = "denyAll()";
        String IS_AUTHENCATED = "isAuthencated()";

    }

    /**
     * Oauth2常量
     */
    interface Oauth2 {

        /**
         * 默认登录URL
         */
        String OAUTH_TOKEN_URL = "/oauth/token";
        /**
         * 手机号登录URL
         */
        String MOBILE_TOKEN_URL = "/mobile/token";
        /**
         * 刷新token url
         */
        String REFRESH_TOKEN_URL = "/refresh/token";
        /**
         * 授权类型
         */
        String GRANT_TYPE = "grant_type";
    }

    /**
     * 前缀
     */
    interface Prefix {


        /**
         * 角色前缀
         */
        String ROLE = "ROLE_";

        /**
         * oauth2前缀
         */
        String REDIS_OAUTH = "oauth:";
    }


}
