function getDiscountListForUser (params) {
    return $axios({
        url: '/discount/forUserByPage',
        method: 'get',
        params
    })
}

function getDiscountListForAdmin (params) {
    return $axios({
        url: '/discount/forAdminByPage',
        method: 'get',
        params
    })
}

const deleteDiscount = (id) => {
    return $axios({
        url: `/discount/${id}`,
        method: 'delete'
    })
}


function addDiscount (params) {
    return $axios({
        url: '/discount',
        method: 'post',
        data: { ...params }
    })
}


function editDiscount (params) {
    return $axios({
        url: '/discount',
        method: 'put',
        data: { ...params }
    })
}

function queryDiscountForUserById (id) {
    return $axios({
        url: `/discount/forUserDetail`,
        method: 'get',
        params: { id }
    })
}

function queryDiscountForAdminById (id) {
    return $axios({
        url: `/discount/forAdminDetail`,
        method: 'get',
        params: { id }
    })
}