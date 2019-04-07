package yanshan.com.xiaokebiao.entity

import yanshan.com.xiaokebiao.common.ConstantPool

/**
 * 事件实体
 *
 * @author itning
 */
class EventEntity {
    var id: ConstantPool.Int? = null
    var msg: String? = null
    var data: Any? = null

    constructor(id: ConstantPool.Int) {
        this.id = id
    }

    constructor(id: ConstantPool.Int, msg: String) {
        this.id = id
        this.msg = msg
    }

    constructor(id: ConstantPool.Int, msg: String, data: Any) {
        this.id = id
        this.msg = msg
        this.data = data
    }
}
