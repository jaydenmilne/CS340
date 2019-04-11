package plugin

import IPersistanceManager
import java.io.File
import java.net.URLClassLoader
import java.net.URL


class PluginManager {
    private val debugMode = true

    private fun findClassInDirectory(directory: String, className: String) : IPersistanceManager {
        // Search the intellij output instead of the /plugins directory
        val dir = File(directory)
        dir.walk().forEach {
            if (it.isFile && it.extension == "jar") {
                try {
                    // Get a class loader and set it up to load the jar file
                    val pluginURL = it.toURI().toURL()
                    val loader = URLClassLoader(arrayOf<URL>(pluginURL))

                    // Load the jar file's plugin class, create and return an instance
                    val managerClass = loader.loadClass(className) as Class<IPersistanceManager>
                    return managerClass.getDeclaredConstructor(null).newInstance()

                } catch (e : ClassNotFoundException) {
                    // must not have been inside of this one
                } catch (e : Exception) {
                    println("Something went wrong loading plugins.")
                    e.printStackTrace()
                }
            }
        }

        return NullPersistenceManager()
    }

    fun loadPlugin(className: String) : IPersistanceManager {
        if (debugMode) {
            return findClassInDirectory("otu", className)
        } else {
            // Enumerate each jar in the /plugins directory
            return findClassInDirectory("plugins", className)
        }
    }
}