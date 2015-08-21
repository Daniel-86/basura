package mx.com.scitum

import groovy.xml.MarkupBuilder

import java.nio.file.Path
import java.nio.file.Paths

final String path = args? args[0]: 'src/tests/basura.xml'

if(!path) return -1;

Path xmlFilePath = Paths.get(path).toAbsolutePath()

def root = new XmlParser().parse(xmlFilePath.toFile())
def routes = root.routes.route
def routeNames = routes.name.inject([]) {result, v -> result << v.text()}
def routeNumbers = routes.number.inject([]) {result, v -> result << v.text()}

def writer = new StringWriter()
def html = new MarkupBuilder(writer)
html.html {
    head {
        title: 'ESTO ES BASURA'
    }
    body(class: 'container-fluid') {
        h1('BASURA')
        table {
            thead {
                tr {
                    th('name')
                    th('number')
                }
            }
            routeNames.eachWithIndex{ name, idx->
                tr {
                    td(name)
                    td(routeNumbers[idx])
                }
            }
        }
    }
}

FileWriter fw = new FileWriter('tabla.html')
fw.write(writer.toString())
fw.close()

