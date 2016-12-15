/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.df.dlogger.printer;

import android.support.annotation.NonNull;

import com.df.dlogger.constant.DLevel;

/**
 * 打印接口.
 */
public interface Printer {

    /**
     * 日志打印输出到文件.
     *
     * @param level   级别
     * @param tag     标签
     * @param message 信息
     */
    void print(@NonNull DLevel level, String tag, String message);
}