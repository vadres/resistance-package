export function upperFirst(sentence) {
    const fn = (word) => {
        const [first, ...rest] = word;  
        return `${first.toUpperCase()}${rest.join('').toLowerCase()}`; 
    }
    
    return sentence.split("_").map(word => fn(word)).join(" ");
}