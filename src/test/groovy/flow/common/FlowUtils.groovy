package flow.common

/**
 * Class provides some util methods
 */
class FlowUtils {

    static Map asMap(Object object) {
        object.class.declaredFields.findAll { !it.synthetic }.collectEntries {
            [ (it.name):object."$it.name" ]
        }
    }
}
