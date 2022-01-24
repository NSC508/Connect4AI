//TODO: FOR ALL GO CLASSES, MAKE SURE TO HAVE THE CHECK THAT ALL GO FUNCTIONS ARE DONE BEFORE PROCEEDING
package neat

import (
	"sort"
)

type EType int

const (
	ETypeInput EType = iota
	ETypeHidden 
	ETypeOutput 
)

type VertexInfo struct {
	etype EType
	index int
}

func newVertex(t EType, i int) *VertexInfo {
	v := new(VertexInfo)
	v.etype = t
	v.index = i
	return v
}

type EdgeInfo struct {
	source int
	destination int 

	weight float64
	enabled bool
	innovation int
}


func newEdgeInfo(s int, d int, w float64, e bool) *EdgeInfo {
	einfo := new(EdgeInfo)
	einfo.source = s
	einfo.destination = d
	einfo.weight = w
	einfo.enabled = e
	einfo.innovation = 0
	return einfo
}

type Genotype struct {
	edges []*EdgeInfo
	vertices []*VertexInfo
	inputs int 
	externals int 
	bracket int
	fitness float64
	adjustedFitness float64
}

func newGenotype() *Genotype {
	g := new(Genotype)
	g.edges = make([]*EdgeInfo, 0)
	g.vertices = make([]*VertexInfo, 0)
	g.inputs = 0
	g.externals = 0
	g.bracket = 0
	g.fitness = 0
	g.adjustedFitness = 0
	return g
}

func (ge *Genotype) AddVertex(t EType, index int) {
	v := newVertex(t, index)
	ge.vertices = append(ge.vertices, v)

	if (v.etype != ETypeHidden) {
		ge.externals++
	}

	if (v.etype == ETypeInput) {
		ge.inputs++
	}
}


func (ge *Genotype) AddEdge(source int, destination int, weight float64, enabled bool, innovation int) {
	e := newEdgeInfo(source, destination, weight, enabled)
	e.innovation = innovation; 
	ge.edges = append(ge.edges, e)
}

//clones a genotype
func (ge *Genotype) Clone() *Genotype {
	g := newGenotype()

	go func() {
		g.vertices = append(g.vertices, ge.vertices...)
	}() 
	
	go func() {
		g.edges = append(g.edges, ge.edges...)
	}()

	return g
}

func (ge *Genotype) SortTopology() {
	go ge.SortVertices()
	go ge.SortEdges()
}

//sorts the vertices of a genotype
func (ge *Genotype) SortVertices() {
	sort.Slice(ge.vertices, func(p, q int) bool {
		return ge.vertices[p].index < ge.vertices[q].index
	})
}

//sorts the edges of a genotype
func (ge *Genotype) SortEdges() {
	sort.Slice(ge.edges, func(p, q int) bool {
		return ge.edges[p].innovation < ge.edges[q].innovation
	})
}
