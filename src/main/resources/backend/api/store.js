function getAllStoresByPage (params) {
    return $axios({
        url: '/store/page',
        method: 'get',
        params
    })
}

function getAllStoresByList () {
    return $axios({
        url: '/store/list',
        method: 'get'
    })
}

const deleteStore = (id) => {
    return $axios({
        url: '/store/${id}',
        method: 'delete'
    })
}


function addStore (params) {
    return $axios({
        url: '/store',
        method: 'post',
        data: { ...params }
    })
}


function editStore (params) {
    return $axios({
        url: '/store',
        method: 'put',
        data: { ...params }
    })
}