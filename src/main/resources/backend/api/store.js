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

function getStoreById (id) {
    return $axios({
        url: '/store',
        method: 'get',
        params: { id }
    })
}

const deleteStore = (id) => {
    return $axios({
        url: `/store/${id}`,
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

const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}