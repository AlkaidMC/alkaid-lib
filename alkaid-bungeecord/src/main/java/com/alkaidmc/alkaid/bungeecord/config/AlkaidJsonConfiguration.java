/*
 * Copyright 2023 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alkaidmc.alkaid.bungeecord.config;

import com.google.gson.Gson;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * <p> zh </p>
 * 辅助开发者加载配置文件 <br>
 * <p> en </p>
 * Helper for developers to load config files. <br>
 */
@SuppressWarnings("unused")
public class AlkaidJsonConfiguration {
    /**
     * <p> zh </p>
     * 配置文件直转数据实体 <br>
     * 先加载插件配置文件夹中的文件 <br>
     * 如果没有则会先从 jar 中读取到插件文件夹再加载一次 <br>
     * 仍然没有将导致异常 <br>
     * 加载成功后使用 Gson 转换成数据实体并返回 <br>
     * 需要重新加载则需要重新调用该方法 <br>
     *
     * <p> en </p>
     * Data entity of config file. <br>
     * First load config file in plugin folder. <br>
     * If not, load it from jar file and load it again. <br>
     * If still not, throw exception. <br>
     * Load successfully, use Gson to convert to data entity and return. <br>
     * If need to reload, call this method again. <br>
     *
     * @param plugin   插件实例 / Plugin instance
     * @param gson     json 解析器 可以使用 AlkaidGsonBuilder.create(); 获取包含 bukkit 序列化类型解析器
     *                 Gson instance. You can use AlkaidGsonBuilder.create();
     *                 to get Gson instance with bukkit serializer.
     * @param resource 在插件 jar 中的资源路径 / Resource path in plugin jar
     * @param path     在插件配置文件夹中的路径 / Path in plugin config folder
     * @param type     数据实体类型 / Data entity type
     * @param <T>      数据实体类型 / Data entity type
     * @return 依赖注入后的数据实体 / Dependency injection data entity
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T> T load(Plugin plugin, Gson gson, String resource, String path, Class<T> type) {
        // 拼接文件路径
        Path filePath = Paths.get(plugin.getDataFolder().getPath() + "/" + path);

        // 检查文件夹
        if (!Files.exists(filePath.getParent())) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 检查资源文件
        File file = new File(plugin.getDataFolder(), path);
        // 否则尝试提取
        if (!file.exists()) {
            // 从 jar 中读取文件
            try {
                file.createNewFile();
                Objects.requireNonNull(plugin.getClass().getResourceAsStream(resource)).
                        transferTo(new FileOutputStream(file));
            } catch (IOException exception) {
                plugin.getLogger().warning("can't found file.");
            }
            // 重新读入
            file = new File(plugin.getDataFolder(), path);
        }
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader buffered = new BufferedReader(input);
            return gson.fromJson(buffered, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
