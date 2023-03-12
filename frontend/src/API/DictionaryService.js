export default class DictionaryService {
    static async getDictionaries() {
        let res = await fetch('http://185.221.196.50:7019/api/v1/dictionary').then(r=>r.json())
        return res.data
    }

    static async getDictionary(dictionaryId, count, skip, search) {
        let res = await fetch(`http://185.221.196.50:7019/api/v1/dictionary/${dictionaryId}?` + new URLSearchParams({
            count, skip, search
        }).toString()).then(r=>r.json())
        return res.data
    }

    static async add(dictionaryId, data) {
        return await fetch('http://185.221.196.50:7019/api/v1/dictionary/' + dictionaryId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(r=>r.json())
    }

    static async getWordById(id) {
        return await fetch('http://185.221.196.50:7019/api/v1/word/' + id).then(r=>r.json())
    }

    static async edit(id, data) {
        return await fetch('http://185.221.196.50:7019/api/v1/word/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(r=>r.json())
    }

    static async delete(id) {
        return await fetch('http://185.221.196.50:7019/api/v1/word/' + id, {
            method: 'DELETE'
        }).then(r=>r.json())
    }
}